# building_calculator

run instruction: 

1. create db in postgres;
2. add properties to application.config
    server.port=
    db.host=
    db.port=
    db.name=
    db.username=
    db.password=
  
3. install maven
4. execute command: mvn spring-boot:run

5. execute curl -X POST \
-H "content-type: application/json" \
-d '{"tree_id":1, "prop": "стоимость"}' \ host:port/calculate

