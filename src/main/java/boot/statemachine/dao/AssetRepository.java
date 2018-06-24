package boot.statemachine.dao;

import boot.statemachine.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findOneByWorkflowId(long workflowId);

}
