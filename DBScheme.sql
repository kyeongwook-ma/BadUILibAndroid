CREATE TABLE `UserTable` (
	`usr_id`	INTEGER NOT NULL,
	`time`	INTEGER,
	PRIMARY KEY(usr_id)
)

CREATE TABLE `BMTable` (
	`usr_id`	INTEGER,
	`seq_id`	INTEGER,
	PRIMARY KEY(usr_id, seq_id),
	FOREIGN KEY(`usr_id`) REFERENCES UserTable ( usr_id )
);

CREATE TABLE `SeqTable` (
	`seq_id`	INTEGER,
	`touch_class`	TEXT,
	`touch_mode`	TEXT,
	`time_stamp`	INTEGER,
	`x`	INTEGER,
	`y`	INTEGER,
	PRIMARY KEY(seq_id),
	FOREIGN KEY(`seq_id`) REFERENCES BMTable ( seq_id )
);
