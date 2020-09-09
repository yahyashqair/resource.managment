# resource.managment
- We have a group of servers in the cloud
- we can spin a server in the clould and each spin operation takes 20 seconds
- each server has x giga ( by defualt 100 )
- each server can reserved by clients 
- client can request to reserve n giga (n <= x)
- the system should support and handle up to 250 requests concurrently

# My solution
technologies : 
-  Spring [  [ DI,IOC  ], 
-  Spring Data Jpa [ simulation of the cloud ],
-  Spring AOP [ logging , thread managment ],
-  Spring REST [   api for clients 'localhost:9090/{numberOfGiga}'  ]
  
