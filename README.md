
## Todo

- [v] prototype: - take 1h
    - REST + RDB + Cache + Message Queue
    - Architecture
- [ ] Add log for o11y
    - log to loki
- [ ] deployment to local and AWS
- [ ] Add Lock and Transaction for inventory
- [ ] Simulation




## Prompt

gened by chat gpt

```bash
我想設計一個 API, 主要是 Create 和 Query 大量資料使用。這個查詢的目的希望是資料能夠有一致性，同時兼顧效能茶訊要好。

以下是 API 的基本結構：

POST /article -- 建立一篇文章
GET /article -- 查詢文章, 查詢文章要有分頁。

這個架構使用的技術如下：

# Java 17
# SpringBoot 2.7
# Gradle
# Cache 使用 Redis
# Database 使用 MySQL
其中查詢 GET /article 實作需要直接從 Redis 取得資料，同時支援 分頁，像是 GET /article?cursor=3423&pageSize=10

而建立文章的時候，資料除了寫入 Database，同時也會回寫到 Cache.

底下資料結構是 article 的 entity:

{
"id": 1,
"subject": "",
"content": "",
"createdAt": ""
}

請根據以上資訊，請幫我寫出範例程式。
```


