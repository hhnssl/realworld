select * from users;
-- ---- Users
INSERT INTO USERS (username, email, password)
VALUES ( 'dataUser', 'dataUser@gmail.com', 'password');
--
INSERT INTO USERS (username, email, password)
VALUES ( 'dataUser2', 'dataUser2@gmail.com', 'password');

INSERT INTO USERS (username, email, password)
VALUES ( 'dataUser3', 'dataUser3@gmail.com', 'password');

INSERT INTO FOLLOWS (user_id, following_id)
VALUES ( '1', '2');

INSERT INTO FOLLOWS (user_id, following_id)
VALUES ( '1', '3');
--
-- INSERT INTO USERS (username, email, password)
-- VALUES ( 'username3', 'username3@gmail.com', 'password');


INSERT INTO ARTICLES(user_id, title, slug, description, body,   created_at, updated_at)
VALUES ('2', 'title1','title1', 'description1', 'body1', '2025-05-26T12:18:14.2771721', '2025-05-26T12:48:14.2771721');

INSERT INTO ARTICLES(user_id, title, slug, description, body,   created_at, updated_at)
VALUES ('3', 'title4','title4', 'description4', 'body4', '2025-05-26T12:48:14.2771721', '2025-05-26T12:48:14.2771721');
--


-- INSERT INTO ARTICLES(author, title, slug, description, body, favorited, favorites_count, created_at, updated_at)
-- VALUES ('author4', 'title4-2','title4-2', 'description4', 'body4', false, 0, '2023-05-26T12:48:14.2771721', '2025-05-26T12:48:14.2771721');
--
-- INSERT INTO ARTICLES(author, title, slug, description, body, favorited, favorites_count, created_at, updated_at)
-- VALUES ('author4', 'title4-3','title4-3', 'description4', 'body4', false, 0, '2022-05-26T12:48:14.2771721', '2025-05-26T12:48:14.2771721');
--
--
-- INSERT INTO ARTICLES(author, title, slug, description, body, favorited, favorites_count, created_at, updated_at)
-- VALUES ('author1', 'title1','title1', 'description1', 'body1', false, 0, '2024-05-26T12:48:14.2771721', '2025-05-26T12:48:14.2771721');
--
--
-- INSERT INTO ARTICLES(author, title, slug, description, body, favorited, favorites_count, created_at, updated_at)
-- VALUES ('author3', 'title3','title3', 'description3', 'body3', false, 0, '2020-05-26T12:48:14.2771721', '2025-05-26T12:48:14.2771721');
--
--
-- INSERT INTO ARTICLES(author, title, slug, description, body, favorited, favorites_count, created_at, updated_at)
-- VALUES ('author2', 'title2','title2', 'description2', 'body2', false, 0, '2021-05-26T12:48:14.2771721', '2025-05-26T12:48:14.2771721');
--
-- INSERT INTO ARTICLES(author, title, slug, description, body, favorited, favorites_count, created_at, updated_at)
-- VALUES ('author2', 'title2-2','title2-2', 'description2-2', 'body2-2', false, 0, '2019-05-26T12:48:14.2771721', '2025-05-26T12:48:14.2771721');
--
--
--
--
-- --
-- INSERT INTO TAGS (name)
-- VALUES ('JS');
--
-- INSERT INTO TAGS (name)
-- VALUES ('TS');
--
-- INSERT INTO TAGS (name)
-- VALUES ('Java');
--
-- INSERT INTO TAGS (name)
-- VALUES ('Python');
--
-- INSERT INTO TAGS (name)
-- VALUES ('Ruby');
--
-- INSERT INTO TAGS (name)
-- VALUES ('PHP');
--
-- --
-- INSERT INTO ARTICLES_TAG_LIST (article_entity_id, tag_list_id)
-- VALUES (1, 1);
-- --
-- INSERT INTO ARTICLES_TAG_LIST (article_entity_id, tag_list_id)
-- VALUES (1, 2);
-- --
-- INSERT INTO ARTICLES_TAG_LIST (article_entity_id, tag_list_id)
-- VALUES (1, 3);
-- --
-- INSERT INTO ARTICLES_TAG_LIST (article_entity_id, tag_list_id)
-- VALUES (2, 4);
-- --
-- INSERT INTO ARTICLES_TAG_LIST (article_entity_id, tag_list_id)
-- VALUES (2, 5);
-- --
-- INSERT INTO ARTICLES_TAG_LIST (article_entity_id, tag_list_id)
-- VALUES (3, 6);
--
--
