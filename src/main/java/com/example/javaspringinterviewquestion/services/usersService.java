@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getActiveUsers() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<List<User>> task1 = () -> userRepository.findActiveUsersByRegion("Region1");
        Callable<List<User>> task2 = () -> userRepository.findActiveUsersByRegion("Region2");
        Callable<List<User>> task3 = () -> userRepository.findActiveUsersByRegion("Region3");

        List<Future<List<User>>> futures = executorService.invokeAll(Arrays.asList(task1, task2, task3));
        List<UserDTO> users = new ArrayList<>();

        for (Future<List<User>> future : futures) {
            try {
                users.addAll(future.get().stream()
                        .map(user -> new UserDTO(user.getName(), user.getEmail()))
                        .toList());
            } catch (Exception e) {
                System.err.println("Error processing user data: " + e.getMessage());
            }
        }

        executorService.shutdown();
        return users;
    }
}
