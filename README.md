# computer-database

## gatling stress test

all the test run without any errors (browse, search, add, edit, delete).

### 1 -

 - Small database : ~400 computers
 - users : 1000 users

| Optimization | Best number of users | Performance gain | 95th percentile |
| --- | :---:  | :---:  | :---: |
| initial configuration | 1000 users | 0% | 8ms |

### 2 -

 - Big database : ~1 000 000 computers
 - users	     : 10 users

| Optimization | users | gain | 95th percentile | comment |
| --- | :---:  | :---:  | :---: | --- |
| initial configuration | 10 users | 0% | 25 612ms | search queries are slowing down everything. |
| add indexes and remove the first wildcard in the like statement, otherwise the index `computer.name` cannot be used | 10 users | 74% | 6 552ms | I couldn't find a way to use indexes while keeping the first `%` in the `like` query |
| stop using the `count()` method when not necessary | 10 users | 30% | 4 598ms | when the size of the list returned is smaller than the size of the page, then there is no point counting the total number of element in the database |
| increased `innodb_buffer_pool_size` (my.cnf) | 10 users | 45% |  2 527ms |
| start tomcat manually and deploy war with maven | 10 users | 80% | 490ms |

### 3 - 

 - Big database : ~1 000 000 computers
 - users	     : 50 users

| Optimization | users | gain | 95th percentile | comment |
| --- | :---:  | :---:  | :---: | --- |
| initial configuration | 50 users | 0% | 5 310ms | the mean value was lot lower (~800ms) some queries were simply extremely slow (max: 25sec) |
| activate mysql query cache | 50 users | 82% | 954ms | with the cache, most queries became very fast (~4ms) but it doesn't solve my problem that some are still too slow (max: 17sec) |
| mount `/tmp` in RAM (tmpfs) | 50 users | 63% | 344ms | everything is faster, but the slow requests are still too slow (max: 1.3sec) |

### 4 -

- Big database : ~1 000 000 computers
- users	     : 500 users

| Optimization | users | gain | 95th percentile | comment |
| --- | :---:  | :---:  | :---: | --- |
| Increased number of connections in mysql & the hikari connection pool to prevent crashing | 500 users | 0% | 31 310ms | The number of connection etc.. was probably wrong. I think some requests were stuck waiting for a connection for too long. Or something else is wrong (cpu, memory & heap are fine).|
| force index used in some queries | 500 users | 75% | 7 800ms | Looking at the mysql logs, I noticed that for some queries mysql was not using the correct index. In the preparedStatement, I manually added a `force index (index_name)` depending on which column was being sorted. (I didn't have much time to test it, maybe it didn't help and the result was simply random luck) |
| create a cache in java for the count() query | 500 users | 99% | 30ms ||

### 5 - 

| Optimization | users | gain | 95th percentile | comment |
| --- | :---:  | :---:  | :---: | --- |
| original configuration | 2500 users | 0% | 500ms | |


### 6 - notes
 - In our current version of mysql, the query cache doesn't work if there is a `-` in the database name.
 - mysql doesn't use the correct index if the offset (in the LIMIT) is too big. Using `force index` fix that. 