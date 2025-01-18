@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/active")
    public List<UserDTO> getActiveUsers() throws InterruptedException {
        UserService userService = new UserService();
        return userService.getActiveUsers();
    }
}
