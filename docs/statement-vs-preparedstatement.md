# Statement vs PreparedStatement

## 📌 Overview

In JDBC, both `Statement` and `PreparedStatement` are used to execute SQL queries, but they differ in how they handle query execution and security.

---

## 🔹 Statement

* Used to execute static SQL queries
* Query is compiled every time it is executed
* Vulnerable to SQL Injection
* Less efficient for repeated queries

### Example

```java
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
```

---

## 🔹 PreparedStatement

* Used for parameterized queries
* Query is precompiled once and reused
* Prevents SQL Injection
* Better performance for repeated execution

### Example

```java
PreparedStatement pstmt = connection.prepareStatement(
    "SELECT * FROM users WHERE name = ?"
);
pstmt.setString(1, name);
ResultSet rs = pstmt.executeQuery();
```

---

## ⚖️ Key Differences

| Feature       | Statement       | PreparedStatement |
| ------------- | --------------- | ----------------- |
| Query Type    | Static          | Parameterized     |
| Compilation   | Every execution | Once              |
| Performance   | Slower          | Faster            |
| SQL Injection | Vulnerable ❌    | Safe ✅            |
| Reusability   | No              | Yes               |

---

## 💉 SQL Injection Example

### Malicious Input

```
' OR '1'='1
```

---

### Using Statement (Vulnerable)

```java
String query = "SELECT * FROM users WHERE name = '" + input + "'";
```

👉 Final Query:

```sql
SELECT * FROM users WHERE name = '' OR '1'='1';
```

➡ Returns ALL rows (security breach)

---

### Using PreparedStatement (Safe)

```java
PreparedStatement pstmt = connection.prepareStatement(
    "SELECT * FROM users WHERE name = ?"
);
pstmt.setString(1, input);
```

➡ Input treated as value, NOT SQL → safe

---

## 🎯 Conclusion

* Always use **PreparedStatement** for user input
* Prevents SQL Injection
* Improves performance and maintainability

---
