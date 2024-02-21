--
-- Here we wire up IDLE cabs to OPEN bookings to create ONGOING bookings scenarios
--

UPDATE live.booking SET
    taxi_id = 'a5a6beef-e705-4a53-b00a-8561c7196c98', -- Driven by Anthony
    status = 'ONGOING'
WHERE id = '95545353-0a0a-439b-b202-dea161235b8c';
UPDATE live.taxi SET status = 'BUSY' WHERE id = 'a5a6beef-e705-4a53-b00a-8561c7196c98';
