INSERT INTO live.booking (id, status, origin, destination, client, taxi_id, created_at) VALUES

-- OPEN bookings
('bc673784-ed14-4b32-9d65-080a588ece2b', 'OPEN', '{"address":"37 Oakland Ave St","city":"Boston","state":"MA","country":"USA","latitude":42.322665961158904,"longitude":-71.0853777121208}', '{"address":"465 Huntington Ave","city":"Boston","state":"MA","country":"USA","latitude":42.338649146049235,"longitude":-71.09351262544922}', '{"name":"John Smith","phone":"+151262544922"}', NULL, now()),
('9e909e58-1995-48be-bcf3-dd91787e8b5f', 'OPEN', '{"address":"37 Oakland Ave St","city":"Boston","state":"MA","country":"USA","latitude":42.322665961158904,"longitude":-71.0853777121208}', '{"address":"465 Huntington Ave","city":"Boston","state":"MA","country":"USA","latitude":42.338649146049235,"longitude":-71.09351262544922}', '{"name":"Maria White","phone":"+16098291028"}', NULL, now()),
('dce1e068-1e15-4354-be91-96452deba51f', 'OPEN', '{"address":"37 Oakland Ave St","city":"Boston","state":"MA","country":"USA","latitude":42.322665961158904,"longitude":-71.0853777121208}', '{"address":"465 Huntington Ave","city":"Boston","state":"MA","country":"USA","latitude":42.338649146049235,"longitude":-71.09351262544922}', '{"name":"Luiz Phillip Bragança","phone":"+1514452434554"}', NULL, now()),

-- Booking that will reach ONGOING
('95545353-0a0a-439b-b202-dea161235b8c', 'OPEN', '{"address":"37 Oakland Ave St","city":"Boston","state":"MA","country":"USA","latitude":42.322665961158904,"longitude":-71.0853777121208}', '{"address":"465 Huntington Ave","city":"Boston","state":"MA","country":"USA","latitude":42.338649146049235,"longitude":-71.09351262544922}', '{"name":"John Smith","phone":"+151262544922"}', NULL, now());
