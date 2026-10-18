package com.example.dispute.api;
import com.example.dispute.domain.*;
import com.example.dispute.service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;
@RestController @RequestMapping("/api/disputes")
public class DisputeController {
 private final DisputeService service;private final EvidenceService evidence;
 public DisputeController(DisputeService s,EvidenceService e){service=s;evidence=e;}
 public record CreateRequest(@NotBlank String transactionReference,@NotNull ClaimantType claimantType,@NotNull @DecimalMin("0.01") BigDecimal amount,@NotBlank @Size(min=3,max=3) String currency,@NotBlank @Size(max=300) String reason){}
 public record TransitionRequest(@NotNull DisputeStatus target,@Size(max=1000) String note,@NotBlank String actor){}
 @PostMapping public Dispute create(@Valid @RequestBody CreateRequest r){return service.create(r.transactionReference(),r.claimantType(),r.amount(),r.currency(),r.reason(),"portal-user");}
 @GetMapping public List<Dispute> list(@RequestParam(required=false) DisputeStatus status){return service.list(status);}
 @GetMapping("/{id}") public Dispute get(@PathVariable Long id){try{return service.get(id);}catch(NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());}}
 @PostMapping("/{id}/transitions") public Dispute transition(@PathVariable Long id,@Valid @RequestBody TransitionRequest r){try{return service.transition(id,r.target(),r.note(),r.actor());}catch(NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());}catch(IllegalArgumentException e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());}}
 @GetMapping("/{id}/history") public List<StateTransition> history(@PathVariable Long id){try{return service.history(id);}catch(NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());}}
 @PostMapping("/{id}/evidence") @ResponseStatus(HttpStatus.CREATED) public EvidenceFile upload(@PathVariable Long id,@RequestParam("file") MultipartFile file){try{return evidence.upload(id,file);}catch(NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());}catch(IllegalArgumentException e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());}}
 @GetMapping("/{id}/evidence") public List<EvidenceFile> evidence(@PathVariable Long id){try{return evidence.list(id);}catch(NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());}}
}
