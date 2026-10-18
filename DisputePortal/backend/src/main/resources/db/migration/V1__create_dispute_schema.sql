CREATE TABLE disputes (
  id BIGSERIAL PRIMARY KEY,
  reference_code VARCHAR(40) NOT NULL UNIQUE,
  transaction_reference VARCHAR(80) NOT NULL,
  claimant_type VARCHAR(20) NOT NULL,
  amount NUMERIC(14,2) NOT NULL CHECK (amount > 0),
  currency CHAR(3) NOT NULL,
  reason VARCHAR(300) NOT NULL,
  status VARCHAR(40) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX ix_disputes_status_created ON disputes(status, created_at DESC);
CREATE TABLE evidence_files (
  id BIGSERIAL PRIMARY KEY,
  dispute_id BIGINT NOT NULL REFERENCES disputes(id),
  original_name VARCHAR(255) NOT NULL,
  stored_name VARCHAR(255) NOT NULL,
  detected_mime_type VARCHAR(150) NOT NULL,
  size_bytes BIGINT NOT NULL,
  uploaded_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE state_transitions (
  id BIGSERIAL PRIMARY KEY,
  dispute_id BIGINT NOT NULL REFERENCES disputes(id),
  from_state VARCHAR(40),
  to_state VARCHAR(40) NOT NULL,
  note VARCHAR(1000),
  actor VARCHAR(120) NOT NULL,
  changed_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE audit_log (
  id BIGSERIAL PRIMARY KEY,
  dispute_id BIGINT NOT NULL REFERENCES disputes(id),
  event_type VARCHAR(80) NOT NULL,
  detail VARCHAR(1000) NOT NULL,
  actor VARCHAR(120) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
