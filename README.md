# まとめーた！！

##用意するもの
 - mongodb
 
##テストデータをいれる
```
mongoimport --db test --collection posts --type json --file test-data-posts.json  --jsonArray --host=127.0.0.1

mongoimport --db test --collection users --type json --file test-data-users.json  --jsonArray --host=127.0.0.1

```