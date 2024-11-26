package com.noxinfinity.pdating.Repository.Other;

import com.noxinfinity.pdating.Entities.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserLocationRepository extends JpaRepository<UserLocation, Long> {
}
