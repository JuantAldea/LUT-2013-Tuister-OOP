DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS followers;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id INTEGER PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    UNIQUE(username) ON CONFLICT ABORT
);

CREATE TABLE posts(   
    id INTEGER PRIMARY KEY,
    body VARCHAR(128) NOT NULL,
    likes INTEGER NOT NULL DEFAULT 0,
    post_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author INTEGER,
    FOREIGN KEY(author) REFERENCES users(id)
);

CREATE TABLE likes(
    user INTEGER,
    post INTEGER,
    FOREIGN KEY(user) REFERENCES users(id),
    FOREIGN KEY(post) REFERENCES post(id),
    PRIMARY KEY(user, post)
);

CREATE TABLE followers(
    follower INTEGER,
    followed INTEGER,
    FOREIGN KEY(follower) REFERENCES users(id),
    FOREIGN KEY(followed) REFERENCES users(id),
    PRIMARY KEY(follower, followed) ON CONFLICT IGNORE
);

