package uz.muhammad.jira.repository.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.muhammad.jira.criteria.MemberCriteria;
import uz.muhammad.jira.domains.auth.Member;
import uz.muhammad.jira.repository.GenericCRUDRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository implements GenericCRUDRepository<Member, MemberCriteria, Long> {
    private static MemberRepository instance;
    private static final List<Member> members = load();

    private static List<Member> load() {
        return new ArrayList<>();
    }

    @Override
    public void create(Member entity) {
        entity.setId(System.currentTimeMillis());
        members.add(entity);

    }

    @Override
    public void update(Member entity) {

    }

    @Override
    public boolean deleteByID(Long aLong) {
        return false;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Member>> findAll(MemberCriteria criteria) {
        return Optional.of(members);
    }

    public static MemberRepository getInstance() {
        if (instance == null) {
            instance = new MemberRepository();
        }
        return instance;
    }
}
