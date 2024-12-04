/******************************************************************
 *
 *   Nick Cwikla / COMP272-001
 *
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     *
     * You are building a course curriculum along with required intermediate
     * exams certifications that must be taken by programmers in order to obtain
     * a new certification called 'master programmer'. In doing so, you are placing
     * prerequisites on intermediate exam certifications that must be taken first.
     * You are allowing the flexibility of taking the exams in any order as long as
     * any exam prerequisites are satisfied.
     *
     * Unfortunately, in the past, your predecessors have accidentally published
     * curriculums and exam schedules that were not possible to complete due to cycles
     * in prerequisites. You want to avoid this embarrassment by making sure you define
     * a curriculum and exam schedule that can be completed.
     *
     * Your goal is to ensure that any student pursuing the certificate of 'master
     * programmer', can complete 'n' certification exams, each being specific to a
     * topic. Some exams have prerequisites of needing to take and pass earlier
     * certificate exams. You do not want to force any order of taking the exams, but
     * you want to make sure that at least one order is possible.
     *
     * This method will save your embarrassment by returning true or false if
     * there is at least one order that can taken of exams.
     *
     * You wrote this method, and in doing so, you represent these 'n' exams as
     * nodes in a graph, numbered from 0 to n-1. And you represent the prerequisite
     * between taking exams as directed edges between two nodes which represent
     * those two exams.
     *
     * Your method expects a 2-dimensional array of exam prerequisites, where
     * prerequisites[i] = [ai, bi] indicating that you must take exam 'bi' first
     * if you want to take exam 'ai'. For example, the pair [0, 1], indicates that
     * to take exam certification '0', you have to first have the certification for
     * exam '1'.
     *
     * The method will return true if you can finish all certification exams.
     * Otherwise, return false (e.g., meaning it is a cyclic or cycle graph).
     *
     *     Example 1:
     *     Input: numExams = 2, prerequisites = [[1,0]]
     *     Output: true
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0. So, it is possible (no
     *     cyclic or cycle graph of prereqs).
     *
     *
     *     Example 2:
     *     Input: numExams = 2, prerequisites = [[1,0],[0,1]]
     *     Output: false
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0, and to take exams 0 you
     *     should also have completed the certification of exam
     *     1. So, it is impossible (it is a cycle graph).
     *
     * @param numExams          - number of exams, which will produce a graph of n nodes
     * @param prerequisites     - 2-dim array of directed edges.
     * @return boolean          - True if all exams can be taken, else false.
     */

    public boolean canFinish(int numExams, 
                             int[][] prerequisites) {
        int numNodes = numExams;  // # of nodes in graph
        // Build directed graph's adjacency list
        ArrayList<Integer>[] adj = getAdjList(numExams, 
                                        prerequisites);
       // Create a new array for the purpose of tracking prerequisites.
        int[] edges = new int[numExams];
       // Generate the prerequisites for every exam.
        for (int i = 0; i < numExams; i++) {
            for (int neighbor : adj[i]) {
               // Increase the count for every prerequisite.
                edges[neighbor]++;
            }
        }
       // Set a queue for processing the exams which have no prerequisites.
        Queue<Integer> queue = new LinkedList<>();
       // Exams with no prerequisites are added to the queue.
        for (int i = 0; i <numExams; i++) {
            if (edges[i] == 0) {
               // If there are no prerequisites add to the queue.
                queue.add(i);
            }
        }
       // Counter for tracking number of exams that have been processed.
        int counter = 0;
       // Process the exams.
        while (!queue.isEmpty()) {
           // Get the next exam that has no prerequisites.
            int node = queue.poll();
           // Increment the counter of exams which have been processed.
            counter++;
           // Lower the prerequisite counter and add to queue.
            for (int neighbor : adj[node]) {
               // Decrement the prerequisite count.
                edges[neighbor]--;
                if (edges[neighbor] == 0) {
                   // Make an addition to the queue if no more prerequisites remain.
                    queue.add(neighbor);
                }
            }
        }
       // Return true if all exams are able to be finished.
        return counter == numExams;
    }

    /**
     * Method getAdjList
     *
     * Building an Adjacency List for the directed graph based on number of nodes
     * and passed in directed edges.
     *
     * @param numNodes      - number of nodes in graph (labeled 0 through n-1) for n nodes
     * @param edges         - 2-dim array of directed edges
     * @return ArrayList<Integer>[]  - An adjacency list representing the provided graph.
     */

    private ArrayList<Integer>[] getAdjList(
            int numNodes, int[][] edges) {

        ArrayList<Integer>[] adj 
                    = new ArrayList[numNodes];      // Create an array of ArrayList ADT

        for (int node = 0; node < numNodes; node++){
            adj[node] = new ArrayList<Integer>();   // Allocate empty ArrayList per node
        }
        for (int[] edge : edges){
            adj[edge[0]].add(edge[1]);              // Add connected node edge [1] for node [0]
        }
        return adj;
    }

    /*
     * Assignment Graphing - Number of groups.
     *
     * There are n people. Some of them are connected
     * as friends forming a group. If person 'a' is
     * connected to person 'b', and person 'b' is
     * connected to person 'c', they form a connected
     * group.
     *
     * Not all groups are interconnected, meaning there
     * can be 1 or more groups depending on how people
     * are connected.
     *
     * This example can be viewed as a graph problem,
     * where people are represented as nodes, and
     * edges between them represent people being
     * connected. In this problem, we are representing
     * this graph externally as an non-directed
     * Adjacency Matrix. And the graph itself may not
     * be fully connected, it can have 1 or more
     * non-connected compoents (subgraphs).
     *
     * Example 1:
     *   Input :
         AdjMatrix = [[0,1,0], [1,0,0], [0,0,0]]
     *   Output: 2
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   Where nodes 0 and 1 aee connected, and node 2
     *   is NOT connected. This forms two groups of
     *   nodes.
     *
     * Example 2:
     *   Input : AdjMatrix = [ [0,0,0], [0,0,0], [0,0,0]]
     *   Output: 3
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   There are no connected nodes, hence forming
     *   three groups.
     *
     * Example 3:
     *   Input : AdjMatrix = [[0,1,0], [1,0,0], [0,1,0]]
     *   Output: 1
     *   Explanation, The adjacency Matrix defined an
     *   undirected graph of 3 nodes (index 0 to 2).
     *   All three nodes are connected by at least one
     *   edge. So they form on large group.
     */

    public int numGroups(int[][] adjMatrix) {
        int numNodes = adjMatrix.length;
        Map<Integer,List<Integer>> graph = new HashMap();
        int i = 0, j =0;

        /*
         * Converting the Graph Adjacency Matrix to
         * an Adjacency List representation. This
         * sample code illustrates a technique to do so.
         */

        for(i = 0; i < numNodes ; i++){
            for(j = 0; j < numNodes; j++){
                if( adjMatrix[i][j] == 1 && i != j ){
                    // Add AdjList for node i if not there
                    graph.putIfAbsent(i, new ArrayList());
                    // Add AdjList for node j if not there
                    graph.putIfAbsent(j, new ArrayList());

                    // Update node i adjList to include node j
                    graph.get(i).add(j);
                    // Update node j adjList to include node i
                    graph.get(j).add(i);
                }
            }
        }
       // Create an array for tracking nodes which have been visited.
        boolean[] visited = new boolean[numNodes];
       // Create a counter for groups which are connected.
        int groups = 0;
       // Go through every node to count number of connected groups.
        for (int node = 0; node < numNodes; node++) {
           // If node has not been visited.
            if (!visited[node]) {
               // Group count is then incremented.
                groups++;
               // Do a DFS to mark all the connected nodes.
                depthFirstSearch(node,graph, visited);
            }
        }
       // Return the number of connected groups.
        return groups;
    }
     /*  Helper method that does a depth first search on the graph.
      */
    private void depthFirstSearch(int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
       // Start with the current node being marked as visited.
        visited[node] = true;
       // If the node has no neighbors, return.
        if (!graph.containsKey(node)) return;
       // Go through all the neighbors which are unvisited.
        for (int neighbor : graph.get(node)) {
           // If neighbor hasn't been visited.
            if (!visited[neighbor]) {
               // Recursively call the depth first search.
                depthFirstSearch(neighbor, graph, visited);
            }
        }
    }
}
