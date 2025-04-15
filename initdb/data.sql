-- Thêm người dùng
INSERT INTO users (id, username, email, "fireBaseId", created_at)
VALUES
  (1, 'john_doe', 'john@example.com', gen_random_uuid(), NOW()),
  (2, 'jane_smith', 'jane@example.com', gen_random_uuid(), NOW());

-- Thêm profile
INSERT INTO profiles (user_id, full_name, age, bio, gender, job, "location", avatar_url, birth_date, created_at)
VALUES
  (1, 'John Doe', 28, 'I love hiking and reading.', 'male', 'Engineer', 'New York', 'https://example.com/john.jpg', '1996-04-10', NOW()),
  (2, 'Jane Smith', 26, 'Coffee lover and traveler.', 'female', 'Designer', 'San Francisco', 'https://example.com/jane.jpg', '1998-08-22', NOW());

-- Thêm hobby
INSERT INTO hobby (id, "name")
VALUES
  (1, 'Reading'),
  (2, 'Hiking'),
  (3, 'Traveling'),
  (4, 'Cooking');

-- Gán hobby cho user (user_hobby)
INSERT INTO user_hobby (user_id, hobby_id)
VALUES
  (1, 1), -- John: Reading
  (1, 2), -- John: Hiking
  (2, 3), -- Jane: Traveling
  (2, 4); -- Jane: Cooking

-- Thêm preferences
INSERT INTO preferences (id, user_id, interested_gender, max_distance, min_age, max_age, dating_purpose)
VALUES
  (1, 1, 'female', 30, 22, 30, 'serious'),
  (2, 2, 'male', 50, 25, 35, 'casual');
