package si.fri.rso.narocilo.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import si.fri.rso.narocilo.lib.NarociloMetadata;
import si.fri.rso.narocilo.models.converters.NarociloMetadataConverter;
import si.fri.rso.narocilo.models.entities.NarociloMetadataEntity;


@RequestScoped
public class NarociloMetadataBean {

    private Logger log = Logger.getLogger(NarociloMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    public List<NarociloMetadata> getNarociloMetadata() {

        TypedQuery<NarociloMetadataEntity> query = em.createNamedQuery(
                "NarociloMetadataEntity.getAll", NarociloMetadataEntity.class);

        List<NarociloMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(NarociloMetadataConverter::toDto).collect(Collectors.toList());

    }

    public List<NarociloMetadata> getNarociloMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, NarociloMetadataEntity.class, queryParameters).stream()
                .map(NarociloMetadataConverter::toDto).collect(Collectors.toList());
    }

    public NarociloMetadata getNarociloMetadata(Integer id) {

        NarociloMetadataEntity narociloMetadataEntity = em.find(NarociloMetadataEntity.class, id);

        if (narociloMetadataEntity == null) {
            throw new NotFoundException();
        }

        NarociloMetadata narociloMetadata = NarociloMetadataConverter.toDto(narociloMetadataEntity);

        return narociloMetadata;
    }

    public NarociloMetadata createNarociloMetadata(NarociloMetadata narociloMetadata) {

        NarociloMetadataEntity narociloMetadataEntity = NarociloMetadataConverter.toEntity(narociloMetadata);

        try {
            beginTx();
            em.persist(narociloMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (narociloMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return NarociloMetadataConverter.toDto(narociloMetadataEntity);
    }

    public NarociloMetadata putNarociloMetadata(Integer id, NarociloMetadata narociloMetadata) {

        NarociloMetadataEntity c = em.find(NarociloMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        NarociloMetadataEntity updatedNarociloMetadataEntity = NarociloMetadataConverter.toEntity(narociloMetadata);

        try {
            beginTx();
            updatedNarociloMetadataEntity.setId(c.getId());
            updatedNarociloMetadataEntity = em.merge(updatedNarociloMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return NarociloMetadataConverter.toDto(updatedNarociloMetadataEntity);
    }

    public boolean deleteNarociloMetadata(Integer id) {

        NarociloMetadataEntity narociloMetadata = em.find(NarociloMetadataEntity.class, id);

        if (narociloMetadata != null) {
            try {
                beginTx();
                em.remove(narociloMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
