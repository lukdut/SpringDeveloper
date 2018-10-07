INSERT INTO acl_class (id, class) VALUES
(1, 'gerasimov.springdev.nosqllibrary.model.Book');

INSERT INTO acl_sid (id, principal, sid) VALUES
  (1, 0, 'ROLE_ADMIN'),
  (2, 0, 'ROLE_AUTHOR'),
  (3, 0, 'ROLE_READER'),
  (4, 1, 'admin'),
  (5, 1, 'author'),
  (6, 1, 'user');