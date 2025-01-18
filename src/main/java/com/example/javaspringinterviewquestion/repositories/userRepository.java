@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findActiveUsersByRegion(String region) {
        String query = "SELECT u FROM User u WHERE u.status = 'active' AND u.region = :region";
        return entityManager.createQuery(query, User.class)
                .setParameter("region", region)
                .getResultList();
    }
}
