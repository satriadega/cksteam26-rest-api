docker run -d \
  --name arsipku-backend \
  --network myapp-network \
  -e ENCRYPTION_KEY="my_super_secret_key" \
  -e SPRING_DATASOURCE_URL="b9818f9cae20380d4be12beac60cf624919bce76427cd59ee2b50ff8cb222c270f9100d33f4c695b768c95d255bb266373bd95eac1cb4a024e57ecf148a8f8f344b169ae34651d7db90fd970f6da8850cbe2a983139aa12d05deb4fecdada610d38c88a65efa33be455de5538ffa5189" \ 
  -e SPRING_DATASOURCE_USERNAME="1f81361a82e6abbad682e9dd3e864dd3" \
  -e SPRING_DATASOURCE_PASSWORD="ba46fa92b04615e39c34fe586d8622e8" \
  -p 8084:8084 \
  satriadega/arsipku-backend:latest
