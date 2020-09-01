import sys
from collections import defaultdict
#import pandas as pd

#Edinburgh Napier University
#Artificial Intelligence
#Breadth First Search cave explorer
#Joel Degner-Budd 40430615

# passing single string argument as file, adds '.cav' extension to string
file_args = str(sys.argv[1]) + '.cav'
# open read operation
file_read = open(file_args, "r")
#stores string file name for printing results to .csn file
results_str = str(sys.argv[1])

# for each index in file_read file
for index in file_read:
    # store each index as int value for each index seperated by ','
    cav_vals = [int(num_val) for num_val in index.split(',')]
    # store number of caves
    cave_num = cav_vals[0]
    # the number of values that are coordinates - number of caves *2 as there are 2 coordinate values
    cave_num_coords = cave_num * 2
    # cave coordinates - the next 14 values starting at position 1 are coordinates
    cave_coords = cav_vals[1: cave_num_coords + 1]
    cave_path_asList = cav_vals[cave_num_coords + 1:]
    # creates rows and columns into a matrix based on the number of caves
    cave_path_matrix = []
    while cave_path_asList != []:
        cave_path_matrix.append(cave_path_asList[:cave_num])
        cave_path_asList = cave_path_asList[cave_num:]

    #copy current matrix and store in store in T_Cave_Path
    T_Cave_Path = cave_path_matrix
    #Transposes matrix using nested list comprehension and stores in T_Cave_Path_Matrix
    T_Cave_Path_Matrix = [[T_Cave_Path[x][y] 
        for x in range(len(T_Cave_Path))] 
        for y in range(len(T_Cave_Path[0]))]

#Cave class, creates cave object where connections are stored in dictionary data structure.
#Dictionary data structure is used to search for the goal using path connections.
class Cave:
    # creates Cave object
    def __init__(self):
        # create empty list
        self.caverowpath = defaultdict(list)
    def addCavePath(self,key,value):
        self.caverowpath[key].append(value)
    def BFS(self, start, goal):
        # BFS Queue
        queue = [[start]]
        valid_path  = False
        visited_node = []
        #take file name, concatenate '.csn' and store in results_args as String
        results_args = str(results_str) + '.csn'

        while queue:
            #pop first path_frontier off the queue
            path_frontier = queue.pop(0) 
            #Get the last node from the path_frontier
            cave_node = path_frontier[-1] 
            #If the popped node is not in 'visited_node' list then:
            if cave_node not in visited_node: 
                #get current caverowpath cave node and store in frontier
                frontier = self.caverowpath[cave_node]
                #for each connection in the frontier
                for x in frontier:
                    #create a new frontier list
                    new_frontier = list(path_frontier)
                    #append connecting node 'x' to new frontier
                    new_frontier.append(x)
                    #append new frontier to queue
                    queue.append(new_frontier)
                    #If the frontier node is goal then write path to file
                    if x == goal:
                        #set valid path to true
                        valid_path = True
                        #open and create file with name stored in results_args string e.g input1.csn
                        with open(results_args, 'w') as results:
                            #for each element stored in new frontier write the value (offset by adding 1 to each value)
                            for x in new_frontier:
                                x = x+1
                                results.write("%d " % x)
                            #create a new line at the end of the file
                            results.write("\n")
                            #if valid_path was found then stop searching for path
                            break
                # Mark node as explored
                visited_node.append(cave_node)
        #if the end of the queue has been reached and a valid path was not found write '0' to file
        if queue == [] and valid_path == False:
            with open(results_args, 'w') as results:
                results.write("%d " % 0)
                results.write("\n")        
# create empty cave object
c = Cave()

#for row element 'x' in 'path' row in T_Cave_Path_Matrix i.e. associates a value with each row in matrix
for x, path in enumerate(T_Cave_Path_Matrix):
    #for elements 'y' in matrix path i.e. 0,0,0,1,0,0,0 etc...
    for y, connected in enumerate(path):
        #if there is a connection in the row i.e. '1'
        if connected:
            #add key value 'x' and connection values'y' to cave path dictionary
            c.addCavePath(x,y)
#use cave object 'c' to call 'BFS' function passing in start position and end goal
c.BFS(0,cave_num-1) 

# closes read operation
file_read.close()
