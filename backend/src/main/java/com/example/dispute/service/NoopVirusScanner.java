package com.example.dispute.service;
import org.springframework.stereotype.Component;
import java.nio.file.Path;
@Component
public class NoopVirusScanner implements VirusScanner {
 @Override public void scan(Path file) { /* Local demo only. Replace with a ClamAV-backed implementation in production. */ }
}
