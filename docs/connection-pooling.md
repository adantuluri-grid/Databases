# Connection Pooling in Java

## 📌 What is Connection Pooling?

Connection pooling is a technique where a pool of database connections is maintained and reused instead of creating a new connection for every request.

---

## ❌ Without Pooling

* Each request creates a new connection
* High overhead
* Slow performance
* Resource exhaustion

---

## ✅ With Pooling

* Connections are reused
* Faster performance
* Better scalability

---

## 🔬 Experiment

### Single Connection DataSource

* Threads: 5
* Query: SELECT pg_sleep(2)
* Total time: ~10 seconds

### HikariCP

* Threads: 5
* Total time: ~2 seconds

---

## ⚖️ Advantages

* Improved performance
* Efficient resource usage
* Better scalability

---

## ⚠️ Disadvantages

* Slight memory overhead
* Requires configuration
* Risk of connection leaks

---

## 🎯 Conclusion

Connection pooling is essential for real-world applications to handle concurrent database operations efficiently.
