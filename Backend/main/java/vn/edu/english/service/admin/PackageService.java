package vn.edu.english.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.english.dto.request.PackageRequest;
import vn.edu.english.entity.PackageEntity;
import vn.edu.english.repository.PackageRepository;
import java.util.List;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    public List<PackageEntity> getAllPackages() {
        return packageRepository.findAll();
    }

    public PackageEntity getPackageById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package không tồn tại"));
    }

    public PackageEntity createPackage(PackageRequest request) {
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setName(request.getName());
        packageEntity.setDescription(request.getDescription());
        packageEntity.setDuration(request.getDuration());
        packageEntity.setStatus(request.getStatus());
        return packageRepository.save(packageEntity);
    }

    public PackageEntity updatePackage(Long id, PackageRequest request){
        PackageEntity pkg = getPackageById(id);
        pkg.setName(request.getName());
        pkg.setDescription(request.getDescription());
        pkg.setDuration(request.getDuration());
        pkg.setStatus(request.getStatus());
        return packageRepository.save(pkg);
    }

    public boolean deletePackage(Long id) {
        if (packageRepository.existsById(id)) {
            packageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
