CREATE TABLE IF NOT EXISTS users(
    id INTEGER PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    UNIQUE(username) ON CONFLICT ABORT
);

CREATE TABLE IF NOT EXISTS posts(   
    id INTEGER PRIMARY KEY,
    body VARCHAR(128) NOT NULL,
    likes INTEGER NOT NULL DEFAULT 0,
    post_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author INTEGER,
    FOREIGN KEY(author) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS likes(
    user INTEGER,
    post INTEGER,
    FOREIGN KEY(user) REFERENCES users(id),
    FOREIGN KEY(post) REFERENCES post(id),
    PRIMARY KEY(user, post)
);

CREATE TABLE IF NOT EXISTS followers(
    follower INTEGER,
    followed INTEGER,
    FOREIGN KEY(follower) REFERENCES users(id),
    FOREIGN KEY(followed) REFERENCES users(id),
    PRIMARY KEY(follower, followed) ON CONFLICT IGNORE
);

