//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Main{
//    public static void main(String[] args)
//    {
//
//        TaskManagementSystem tms=new TaskManagementSystem("JIRA");
//        User user=  tms.createUser("Nikhil");
//        User user1=  tms.createUser("Nickey");
//        tms.createProject("flowwise");
//        tms.createBoard("flowwise","ORC");
//        tms.createTask("flowwise","ORC","Dynamic routing",user,LocalDateTime.now().plusDays(1),Priority.MEDIUM,"Description of the task");
//        tms.addComment("flowwise","ORC","Dynamic routing",user1,"DO this DR right");
//        tms.showTask("flowwise","ORC","Dynamic routing");
//        tms.moveTask("flowwise","ORC","Dynamic routing",TaskStatus.DONE);
//        tms.filterTask(null,Priority.MEDIUM);
//        tms.filterTask(user,Priority.MEDIUM);
//    }
//}
//enum Priority{
//    LOW,MEDIUM,HIGH
//}
//enum TaskStatus{
//    TODO,IN_PROGRESS,DONE
//}
//class User{
//    private static int count=1;
//    private String userName;
//    private int id;
//    public User(String name)
//    {
//        this.userName =name;
//        this.id=count++;
//    }
//    public String getUserName()
//    {
//        return userName;
//    }
//    public int getId()
//    {
//        return id;
//    }
//}
//class Comment{
//    private User user;
//    private String comment;
//    private LocalDateTime addedOn;
//    public Comment(User user,String comment)
//    {
//        this.user=user;
//        this.addedOn=LocalDateTime.now();
//        this.comment=comment;
//    }
//    public User getUser()
//    {
//        return user;
//    }
//    public LocalDateTime getAddedOn()
//    {
//        return addedOn;
//    }
//    public String getCommentMessage()
//    {
//        return comment;
//    }
//}
//class Task{
//    private static int count=1;
//    private int id;
//    private String name;
//    private TaskStatus status;
//    private User assignee;
//    private LocalDateTime dueDate;
//    private List<Comment> comments;
//    private Priority priority;
//    private String boardName;
//    private String projectName;
//    private String description;
//    public Task(String name,User assignee,LocalDateTime dueDate,Priority priority,String boardName,String projectName,String description)
//    {
//        this.id=count++;
//        this.name=name;
//        this.status=TaskStatus.TODO;
//        this.assignee=assignee;
//        this.dueDate=dueDate;
//        this.comments=new ArrayList<>();
//        this.priority=priority;
//        this.boardName=boardName;
//        this.projectName=projectName;
//        this.description=description;
//    }
//    public String getDescription()
//    {
//        return description;
//    }
//    public String getBoardName()
//    {
//        return boardName;
//    }
//    public String getProjectName()
//    {
//        return projectName;
//    }
//    public void updateStatus(TaskStatus taskStatus)
//    {
//        this.status=taskStatus;
//        System.out.println("Task with name "+name+" moved to status "+status);
//    }
//    public void updateAssignee(User user)
//    {
//        this.assignee=user;
//        System.out.println("Task with name "+name+" assigned to user "+user.getUserName());
//
//    }
//    public Priority getPriority()
//    {
//        return priority;
//    }
//    public void addComment(Comment comment)
//    {
//        this.comments.add(comment);
//    }
//    public List<Comment> getComments()
//    {
//        return comments;
//    }
//    public TaskStatus getStatus()
//    {
//        return status;
//    }
//    public String getName()
//    {
//        return name;
//    }
//    public User getAssignee() {
//        return assignee;
//    }
//    public LocalDateTime getDueDate() {
//        return dueDate;
//    }
//}
//class Board{
//    private String name;
//    private Map<String,Task> tasks;
//    public Board(String name)
//    {
//        this.name=name;
//        this.tasks=new HashMap<>();
//    }
//    public String getName()
//    {
//        return name;
//    }
//    public Task getTaskByName(String name)
//    {
//        if(!tasks.containsKey(name))
//        {
//            return null;
//        }
//        return tasks.get(name);
//    }
//    public void addTask(Task task)
//    {
//        this.tasks.put(task.getName(),task);
//    }
//}
//class Project{
//    private String name;
//    private Map<String,Board> boards;
//    public Project(String name)
//    {
//        this.name=name;
//        this.boards=new HashMap<>();
//    }
//    public String getName()
//    {
//        return name;
//    }
//    public Board getBoardByName(String name)
//    {
//        if(!boards.containsKey(name))
//        {
//            return null;
//        }
//        return boards.get(name);
//    }
//    public void addBoard(Board board)
//    {
//        this.boards.put(board.getName(),board);
//    }
//}
//
//class TaskManagementSystem{
//    private String name;
//    private HashMap<String,Project> projectMap;
//    private HashMap<String,User> userMap;
//    private HashMap<String,Task> taskMap;
//
//    public TaskManagementSystem(String name)
//    {
//        this.name=name;
//        this.projectMap=new HashMap<>();
//        this.userMap=new HashMap<>();
//        this.taskMap=new HashMap<>();
//    }
//    public void createProject(String name)
//    {
//        if(projectMap.containsKey(name))
//        {
//            System.out.println("Project with name "+name+" already exists");
//            return;
//        }
//        Project project=new Project(name);
//        this.projectMap.put(name,project);
//        System.out.println("Project with name "+name+" created SuccessFully");
//    }
//    public User createUser(String userName){
//        if(userMap.containsKey(userName))
//        {
//            System.out.println("User with name "+userName+" already exists");
//            return null;
//        }
//        User user=new User(userName);
//        this.userMap.put(userName,user);
//        return user;
//    }
//    public void createBoard(String projectName,String boardName)
//    {
//        if(!projectMap.containsKey(projectName))
//        {
//            System.out.println("No such project with "+projectName+" exists");
//            return;
//        }
//        Board board=new Board(boardName);
//        Project project=projectMap.get(projectName);
//        project.addBoard(board);
//        System.out.println(" Board with name "+boardName+" is created successfully on project "+projectName);
//    }
//    public void createTask(String projectName,String boardName,String name,User assignee,LocalDateTime dueDate,Priority priority,String desc)
//    {
//        if(assignee==null)
//        {
//            System.out.println("Please provide the valid assignee");
//            return;
//        }
//        if(!projectMap.containsKey(projectName))
//        {
//            System.out.println("No such project with "+projectName+"name exists");
//            return;
//        }
//        Project project=projectMap.get(projectName);
//        Board board= project.getBoardByName(boardName);
//        if(board==null)
//        {
//            System.out.println("No such Board with "+projectName+" name exists under project "+projectName);
//            return;
//        }
//        Task task=board.getTaskByName(name);
//        if(task!=null)
//        {
//            System.out.println("task with "+name+" already exists under board "+boardName+" under project "+projectName);
//            return;
//        }
//        task=new Task(name,assignee,dueDate,priority,boardName,projectName,desc);
//        this.taskMap.put(getTaskKey(projectName,boardName,name),task);
//        board.addTask(task);
//        System.out.println("task with "+name+" Created successfully under board "+boardName+" under project "+projectName);
//    }
//    public void moveTask(String projectName,String boardName,String name,TaskStatus taskStatus)
//    {if(!taskMap.containsKey(getTaskKey(projectName,boardName,name)))
//    {
//        System.out.println("No such Task with "+name+" name exists");
//        return;
//    }
//        Task task= taskMap.get(getTaskKey(projectName,boardName,name));
//        task.updateStatus(taskStatus);
//    }
//
//    public void assignTaskToUser(String projectName,String boardName,String name,User user)
//    {if(!taskMap.containsKey(getTaskKey(projectName,boardName,name)))
//    {
//        System.out.println("No such Task with "+name+" name exists");
//        return;
//    }
//        Task task= taskMap.get(getTaskKey(projectName,boardName,name));
//        task.updateAssignee(user);
//    }
//    public void addComment(String projectName,String boardName,String taskName,User user,String comment)
//    {
//        if(!taskMap.containsKey(getTaskKey(projectName,boardName,taskName)))
//        {
//            System.out.println("No such Task with "+taskName+" name exists");
//            return;
//        }
//        Task task= taskMap.get(getTaskKey(projectName,boardName,taskName));
//        Comment comment1=new Comment(user,comment);
//        task.addComment(comment1);
//    }
//
//    public void showTask(String projectName,String boardName,String name)
//    {
//        if(!taskMap.containsKey(getTaskKey(projectName,boardName,name)))
//        {
//            System.out.println("No such Task with "+name+" name exists");
//            return;
//        }
//        Task task= taskMap.get(getTaskKey(projectName,boardName,name));
//        System.out.println("Task  name "+name+" Status "+task.getStatus()+" assignee "+task.getAssignee().getUserName()+" priority "+ task.getPriority()+" Due Date "+task.getDueDate()+" BoardName "+task.getBoardName()+" ProjectName "+ task.getProjectName()+" Description "+task.getDescription());
//        System.out.println(" < COMMENTS > ");
//        for(Comment comment : task.getComments())
//        {
//            System.out.println("Comment by user "+comment.getUser().getUserName()+" :: AddedOn "+comment.getAddedOn()+" :: Comment " + comment.getCommentMessage());
//        }
//    }
//    public List<Task> filterTask(User user,Priority priority)
//    {
//        List<Task> resultTaskList=new ArrayList<>();
//        for(Task task:taskMap.values())
//        {
//            if(user!=null && task.getAssignee().getId()!=user.getId())
//            {
//                continue;
//            }
//            if(priority!=null && task.getPriority()!=priority)
//            {
//                continue;
//            }
//            resultTaskList.add(task);
//            showTask(task.getProjectName(),task.getBoardName(),task.getName());
//        }
//        return resultTaskList;
//    }
//    private String getTaskKey(String projectName,String boardName,String taskName)
//    {
//        return projectName+":"+boardName+":"+taskName;
//    }
//
//}