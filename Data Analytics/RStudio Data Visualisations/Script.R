#list of libraries
#Mass, ggplot2, fsmb, readxl, lattice, gridExtra, ggfortify, dplyr

#set factors
dataset$Gender = as.factor(dataset$Gender)
dataset$Location = as.factor(dataset$Location)
dataset$Age.Band = as.factor(dataset$Age.Band)

#basic code
ggplot(dataset, aes(x=Age.Band))+geom_bar()

#percentages as a table
prop.table(table(dataset$Gender))
prop.table(table(dataset$Location))
prop.table(table(dataset$Age.Band))


#----------------- NEW CODE ---------------------

#Theme
ggplot(dataset, aes(x = Gender)) + theme_bw() + geom_bar() 
+ labs(x = "Gender Type",y = "Gender Count", title = "Total number of participants by Gender")

#2 dimensional aspects
  #works but has an issue with command line spacing
  ggplot(dataset, aes(x = Location, y = Completion.Time, fill = Gender)) + theme_bw() + geom_bar() 
  + labs(x = "Gender Type",y = "Gender Count", title = "Total number of participants by Gender")
  #this one works
  ggplot(dataset, aes(x = Location,fill = Gender)) + theme_bw() + geom_bar() +labs(x = "Gender Type",y = "Gender Count", title = "Total number of participants by Gender")
  #stored in df
  df = ggplot(data = dataset, aes(x = Location,fill = Gender)) + theme_bw() + geom_bar() +labs(x = "Gender Type",y = "Gender Count", title = "Total number of participants by Gender")
  #Completion Time by area and gender
  df2 = ggplot(data = dataset, aes(x = Location,y = Completion.Time, fill = Gender)) + theme_bw() + geom_boxplot() +labs(x = "Location",y = "Completion Time", title = "Completion time of each area by Gender")
  dftest = ggplot(data = dataset, aes(x = Location,y = Completion.Time, fill = Gender)) + theme_bw() + geom_density() +labs(x = "Location",y = "Completion Time", title = "Completion time of each area by Gender")
  
  #multidimentional test
  grid.arrange(df,df2,ncol=2)
  
  #VERY USEFUL
  ggplot(dataset, aes(x = Location, y=Completion.Time, fill = Gender)) + 
    theme_bw() +
    facet_wrap(~ Age.Band) +
    geom_boxplot() +
    labs(y = "Completion Time",
         title = "Task Completion time by Gender, Location and Age Category")+theme(plot.title = element_text(hjust = 0.5))
  
  #Messing about
  ggplot(dataset, aes(x = Age, fill = Age.Band)) + 
    theme_bw() +
    facet_wrap(~ Gender) +
    geom_histogram(binwidth = 1) +
    labs(y = "Completion Time",
         title = "Task Completion time by Gender, Location and Age Category")
  
  ggplot(dataset, aes(x = Part.C.Score, fill = Age.Band)) + 
    theme_bw() +
    #facet_wrap(~ Gender) +
    geom_histogram(binwidth = 1) +
    labs(y = "Completion Time",
         title = "Task Completion time by Gender, Location and Age Category")
  
  ggplot(dataset, aes(x = Part.A.Score, y=Completion.Time, fill = Age.Band)) + 
    +     theme_bw() +
    +     #facet_wrap(~ Gender) +
    +     geom_boxplot() +
    +     labs(y = "Completion Time",
               +          title = "Task Completion time by Gender, Location and Age Category")
  
  #Geom Point - not sure if this is useful
  ggplot(dataset, aes(x=Age, y=Part.A.Score), color="steelblue") + geom_point() + geom_smooth()
  ggplot(dataset) + geom_point(aes(x=Gender, y=Completion.Time)) + geom_smooth(aes(x=Gender, y=Completion.Time))
  
  ggplot(dataset, aes(x = Part.B.Score, y = Completion.Time, fill =Gender)) + 
    theme_bw() +
    geom_smooth() +
    labs(y = "Completion Time",
         title = "Task Completion time by Gender, Location and Age Category")
  
  ggplot(dataset, aes(x = Part.A.Score, y = Completion.Time, fill =Location)) + 
    theme_bw() +
    geom_smooth() +
    labs(y = "Completion Time",title = "Task Completion time by Gender, Location and Age Category")
  
  ggplot(dataset, aes(x = Completion.Time, y = Part.A.Score, fill =Location)) + 
    theme_bw() +
    geom_smooth() +
    labs(x = "Completion Time",y= "Part A score" ,title = "Task Completion time by Gender, Location and Age Category")
  
  #Geom Line
  
#----------------- OLD STUFF---------------------
  
#This line of code displays data along the x axis and scales the y axis to a percentage value.
# Note - this chart in particular is saved under frame 'df'
#ggplot(data = dataset, aes(x=Part.A.Score,y = 1/1000)) +geom_bar(stat = "identity")

#This line of code would display Age Band as a percentage value - note, this is not currently stored in a date frame
#ggplot(dataset, aes(x=Age.Band, y=1/10000)) + geom_bar(stat="identity")

#variables
#array = dataset$Part.A.Score
#array2 = dataset$Part.B.Score
#array3 = dataset$Part.C.Score

#DF
#df = data.frame(var = c(dataset$Age.Band))
#ggplot(df, aes(x=var)) + geom_bar()

#DF2
#df2 = data.frame(var=c("M","O","U","Y"), nums=c(dataset$Age.Band))
#ggplot(df2, aes(x=var)) + geom_bar()

  #array
  #ggplot(df2, aes(x=age_groups,array)) + geom_boxplot()

  #better example
  #df2 = data.frame(age_groups=c("M","O","U","Y"), nums=c(dataset$Age.Band))
  #ggplot(df2, aes(x=age_groups)) + geom_bar()
  

