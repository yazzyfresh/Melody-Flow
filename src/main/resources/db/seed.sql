USE melody_flow;

-- Add the default user role
INSERT INTO roles (name)
VALUES ('USER')
    ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Add the default administrator role
INSERT INTO roles (name)
VALUES ('ADMIN')
    ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Add a sample artist if it does not exist
INSERT INTO artists (external_id, image_url, name)
VALUES ('MF-TEST-ARTIST-001', NULL, 'Melody Flow Demo Artist')
    ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Add a sample venue if it does not exist
INSERT INTO venues (address, city, external_id, name, state)
VALUES (
           NULL,
           'Tampa',
           'MF-TEST-VENUE-001',
           'Melody Flow Demo Venue',
           'Florida'
       )
    ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Add a sample concert if it does not exist
INSERT INTO concerts (
    event_date,
    external_id,
    name,
    ticket_url,
    artist_id,
    venue_id
)
SELECT
    '2026-11-15 19:30:00',
    'MF-TEST-CONCERT-001',
    'Melody Flow Demo Concert',
    NULL,
    (SELECT id FROM artists
     WHERE external_id = 'MF-TEST-ARTIST-001'),
    (SELECT id FROM venues
     WHERE external_id = 'MF-TEST-VENUE-001')
    WHERE NOT EXISTS (
    SELECT 1 FROM concerts
    WHERE external_id = 'MF-TEST-CONCERT-001'
);