#Relationships discovered
#Linear relationship between Age, Part B Score, and Age
test = ggplot(data=dataset,aes(x=Part.B.Score, y=Age,col=Age.Band,shape=Age.Band)) + geom_point()

#Higher number of female participents than male
ggplot(data=dataset,aes(x=Age.Band, fill = Gender)) + geom_bar()

#Variance in Part C Score and Location
ggplot(data=dataset,aes(x=Location,y=Part.C.Score,fill=Location)) + geom_boxplot()

#Completion time by Gender, Location and Age Category
ggplot(dataset, aes(x = Location, y=Completion.Time, fill = Gender)) + 
  theme_bw() +
  facet_wrap(~ Age.Band) +
  geom_boxplot() +
  labs(y = "Completion Time",
       title = "Task Completion time by Gender, Location and Age Category")+theme(plot.title = element_text(hjust = 0.5))

#Part C Score by Age Band and Location
ggplot(dataset, aes(x = Part.C.Score, col = Age.Band)) + 
  theme_bw() +
  facet_grid(~ Location) +
  geom_freqpoly() +
  labs(x = "Completion Time",
       title = "Task Completion time by Gender, Location and Age Category")+theme(plot.title = element_text(hjust = 0.5))

#comparison of Score sets by completion time and Age.Band
CompTimeA = ggplot(dataset, aes(x = Part.A.Score, col = Age.Band)) + 
  theme_bw() +
  geom_freqpoly() +
  labs(x = "Completion Time",
       title = "Part.A.Score")+theme(plot.title = element_text(hjust = 0.5))
CompTimeB = ggplot(dataset, aes(x = Part.B.Score, col = Age.Band)) + 
  theme_bw() +
  geom_freqpoly() +
  labs(x = "Completion Time",
       title = "Part.B.Score")+theme(plot.title = element_text(hjust = 0.5))
CompTimeC = ggplot(dataset, aes(x = Part.C.Score, col = Age.Band)) + 
  theme_bw() +
  geom_freqpoly() +
  labs(x = "Completion Time",
       title = "Part.C.Score")+theme(plot.title = element_text(hjust = 0.5))

grid.arrange(CompTimeA,CompTimeB,CompTimeC,ncol=3)

#Age with completion time
#Men is consistent, women take longer to complete task as age decreases?
ggplot(data=dataset,aes(x=Completion.Time,y = Age, col = Gender,fill = Gender)) + geom_smooth()
ggplot(data=dataset,aes(x=Completion.Time,y = Age, col = Gender)) + geom_smooth(se=F)

#Old Stuff
#plot(dataset$Age~dataset$Part.B.Score)
#ggplot(data=dataset,aes(x=Part.B.Score, y=Age,col=Age.Band)) + geom_point()