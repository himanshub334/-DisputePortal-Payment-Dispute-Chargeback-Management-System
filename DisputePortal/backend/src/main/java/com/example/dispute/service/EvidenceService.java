package com.example.dispute.service;
import com.example.dispute.domain.*;
import com.example.dispute.repository.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;
@Service
public class EvidenceService {
 private final EvidenceRepository evidence; private final DisputeRepository disputes; private final VirusScanner scanner; private final Tika tika=new Tika();
 @Value("${app.upload-dir:./uploads}") private String uploadDir;
 @Value("${app.max-upload-bytes:10485760}") private long maxBytes;
 private static final Map<String,Set<String>> ALLOWED=Map.of(
  "pdf",Set.of("application/pdf"),"png",Set.of("image/png"),"jpg",Set.of("image/jpeg"),"jpeg",Set.of("image/jpeg"),
  "txt",Set.of("text/plain"),"csv",Set.of("text/plain","text/csv","application/vnd.ms-excel"));
 public EvidenceService(EvidenceRepository evidence,DisputeRepository disputes,VirusScanner scanner){this.evidence=evidence;this.disputes=disputes;this.scanner=scanner;}
 @Transactional
 public EvidenceFile upload(Long disputeId,MultipartFile file){
  Dispute dispute=disputes.findById(disputeId).orElseThrow(()->new NoSuchElementException("Dispute not found"));
  if(file==null||file.isEmpty())throw new IllegalArgumentException("File is empty");
  if(file.getSize()>maxBytes)throw new IllegalArgumentException("File exceeds maximum size");
  String original=Objects.requireNonNullElse(file.getOriginalFilename(),"evidence.bin");
  String clean=Paths.get(original).getFileName().toString();
  int dot=clean.lastIndexOf('.'); String ext=dot<0?"":clean.substring(dot+1).toLowerCase(Locale.ROOT);
  if(!ALLOWED.containsKey(ext))throw new IllegalArgumentException("Unsupported file extension");
  try {
   String mime; try(InputStream in=file.getInputStream()){mime=tika.detect(in,clean);}
   if(!ALLOWED.get(ext).contains(mime))throw new IllegalArgumentException("File content does not match allowed type");
   Path dir=Paths.get(uploadDir).toAbsolutePath().normalize();Files.createDirectories(dir);
   String stored=UUID.randomUUID()+"."+ext;Path target=dir.resolve(stored).normalize();
   if(!target.getParent().equals(dir))throw new IllegalArgumentException("Invalid filename");
   Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
   scanner.scan(target);
   return evidence.save(new EvidenceFile(dispute,clean,stored,mime,file.getSize()));
  } catch(IllegalArgumentException e){throw e;} catch(Exception e){throw new IllegalStateException("Unable to validate or store evidence",e);}
 }
 public List<EvidenceFile> list(Long disputeId){if(!disputes.existsById(disputeId))throw new NoSuchElementException("Dispute not found");return evidence.findByDisputeIdOrderByUploadedAtDesc(disputeId);}
}
