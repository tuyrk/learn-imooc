# 查询需求例题

### 查询需求

1. 名字中包含雨并且年龄小于40

   ```mysql
   name like '%雨%' and age<40
   ```

2. 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空

   ```mysql
   name like '%雨%' and age between 20 and 40 and email is not null
   ```

3. 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列

   ```mysql
   name like '王%' or age>=25 order by age desc,id asc
   ```

4. 创建日期为2019年2月14日并且直属上级为名字为王姓

   ```mysql
   date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
   ```

5. 名字为王姓并且（年龄小于40或邮箱不为空）

   ```mysql
   name like '王%' and (age<40 or email is not null)
   ```

6. 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）

   ```mysql
   name like '王%' or (age<40 and age>20 and email is not null)
   ```

7. （年龄小于40或邮箱不为空）并且名字为王姓
   
   ```mysql
   (age<40 or email is not null) and name like '王%'
   ```
   
8. 年龄为30、31、34、35

   ```mysql
   age in (30、31、34、35)
   ```

9. 只返回满足条件的其中一条语句即可

   ```mysql
   limit 1
   ```

### SELECT中字段不全部出现的查询

名字中包含雨并且年龄小于40(需求1加强版)

1. 第一种情况

   ```mysql
   select id, name from user
   where name like '%雨%' and age < 40;
   ```

2. 第二种情况

   ```mysql
   select id, name, age, email
   from user
   where name like '%雨%' and age < 40;
   ```

### 统计查询

按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组。

```mysql
select avg(age) avg_age, min(age) min_age, max(age) max_age
from user
group by manager_id
having sum(age) < 500;
```

