package com.noxinfinity.pdating.Domains.ConversationManagement;

import com.noxinfinity.pdating.Entities.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversations, Long> {

}
