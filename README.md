# computer-database

## gatling benchmark

all the test run without any errors (browse, search, add, edit, delete).

Small database : ~400 computers
users 	       : 1000 users

| Optimization | Best number of users | Performance gain | 95th percentile |
| --- | --- | --- | --- |
| initial configuration | 1000 users | /% | 8ms |

Big database : ~1 000 000 computers
users	     : 10 users

| Optimization | Best number of users | Performance gain | 95th percentile |
| --- | --- | --- | --- |
| initial configuration | 10 users | /% | 25 612ms |
| add indexes (and remove the first wildcard in the like statement, otherwise the index computer.name cannot be used) | 10 users | /% | 6 552ms |
| stop using the count() method when not necessary | 10 users | /% | 4 598ms |
| increased innodb_buffer_pool_size (my.cnf) | 10 users | /% |  2 527ms |
| start tomcat manually and deploy with maven | 10 users | /% | 490ms |

Big database : ~1 000 000 computers
users	     : 50 users

| Optimization | Best number of users | Performance gain | 95th percentile | comment |
| --- | --- | --- | --- |
| initial configuration | 50 users | /% | 5 310ms | the mean value was lot lower (~800ms) some queries were simply very very long (max: 25sec) |
| activate mysql query cache | 50 users | /% | 954ms | because of the cache, most queries are very fast (~4ms) but it doesn't solve my problem that some are extremely slow (max: 17sec) |
| mount /tmp in RAM (tmpfs) | 50 users | /% | 344ms | everything is faster, but the slow requests remain slow... (max: 1.3sec) |

Big database : ~1 000 000 computers
users	     : 500 users

| Optimization | Best number of users | Performance gain | 95th percentile | comment |
| --- | --- | --- | --- |
| increased number of connections in mysql & the hikari connection pool to prevent crashing | 500 users | /% | 31 310ms | the number of connection etc.. was probably wrong. I think some requests were stuck waiting for a connection for too long. Everything run without any errors. |
| force index use in some queries | 500 users | /% | 7 800ms | Looking at the mysql logs, I noticed that for some queries mysql was not using the right index. In the preparedStatement, I manually added a 'force index (index_name)' depending on which column was being sorted. (I didn't have much time to test it, maybe it didn't help and the result was simply luck) |

note:

 - I'm not sure why the 'force index' is needed. When I execute the query directly from mysqlclient and check with 'explain', the index is used. Maybe there is different config between mysqlclient and mysqld?
 - The query cache doesn't work on 'SELECT count(*) FROM computer;'. Once again, the cache works when I execute it directly from mysqlclient. However, caching this query could easily be handled directly from the application. 