INSERT INTO nodes (node_name, host, port) VALUES
                                              ('node-1', 'localhost', 5001),
                                              ('node-2', 'localhost', 5002),
                                              ('node-3', 'localhost', 5003);

INSERT INTO node_state VALUES
                           (1, 1, 'LEADER'),
                           (2, 1, 'FOLLOWER'),
                           (3, 1, 'FOLLOWER');

INSERT INTO logs (node_id, term, log_index, command, is_committed) VALUES
                                                                       (1, 1, 1, 'SET x=10', TRUE),
                                                                       (1, 1, 2, 'SET y=20', TRUE),
                                                                       (2, 1, 1, 'SET x=10', TRUE),
                                                                       (3, 1, 1, 'SET x=10', TRUE);

INSERT INTO terms (term_number, leader_id) VALUES
    (1, 1);

INSERT INTO peers VALUES
                      (1,2),(1,3),(2,1),(2,3),(3,1),(3,2);