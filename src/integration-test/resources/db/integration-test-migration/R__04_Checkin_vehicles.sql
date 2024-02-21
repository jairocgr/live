--
-- Here we assign drivers to cabs and put them in the IDLE state
--

UPDATE live.taxi SET
    status = 'IDLE',
    latitude = 42.2925427212,
    longitude = -71.070664592,
    driver_id = 'a5bbc872-93b3-4645-800f-289a40b52672' -- Mark
WHERE id = '3c3a2571-1a4f-4914-9740-18ca3d88c359';

UPDATE live.taxi SET
    status = 'IDLE',
    latitude = 42.32199220929702,
    longitude = -71.1828428817516,
    driver_id = '74c003e5-5410-4f6b-bbe6-52f5b2a90ce0' -- Rafael
WHERE id = 'd6935b73-3e56-4c96-a7d0-82c60cca8066';

-- Taxis that will end up being BUSY, but need to be IDLE first
UPDATE live.taxi SET
    status = 'IDLE',
    latitude = 42.26983159256986,
    longitude = -71.02417522513761,
    driver_id = 'beacff30-31e7-426f-951c-fdfe75c6f1b3' -- Tony
WHERE id = 'a5a6beef-e705-4a53-b00a-8561c7196c98';
