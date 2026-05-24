package skyscraper.geaografia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skyscraper.geaografia.repository.RegionRepository;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

}
