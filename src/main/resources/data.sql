-- Seed sample data (only if table is empty)
INSERT INTO users (name, email)
SELECT 'Ford Prefect', 'ford.prefect@hitchhikers.guide'
WHERE NOT EXISTS (SELECT 1 FROM users);

INSERT INTO users (name, email)
SELECT 'Arthur Dent', 'arthur.dent@earth.com'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE name = 'Arthur Dent');

INSERT INTO users (name, email)
SELECT 'Zaphod Beeblebrox', 'zaphod@heartofgold.ship'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE name = 'Zaphod Beeblebrox');
