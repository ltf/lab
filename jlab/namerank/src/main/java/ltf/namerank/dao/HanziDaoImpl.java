package ltf.namerank.dao;

import ltf.namerank.entity.Hanzi;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author ltf
 * @since 16/6/8, 上午10:27
 */
@Component
public class HanziDaoImpl implements HanziDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveHanzi(Collection<Hanzi> hanzi) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Hanzi zi : hanzi) session.save(zi);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Collection loadHanzi(String kword) {
        return null;
    }
}
