package vn.edu.english.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.english.dto.request.PackageRequest;
import vn.edu.english.entity.PackageEntity;
import vn.edu.english.service.admin.PackageService;

import java.util.List;

@RestController
@RequestMapping("/admin/packages")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @GetMapping
    public List<PackageEntity> getAllPackage() {
        return packageService.getAllPackages();
    }

    @GetMapping("/{id}")
    PackageEntity getPackage(@PathVariable("id") Long packageId){
        return packageService.getPackageById(packageId);
    }

    @PostMapping
    public PackageEntity createPackage(@RequestBody PackageRequest packages) {
        return packageService.createPackage(packages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackageEntity> updatePackage(@PathVariable Long id, @RequestBody PackageRequest packages) {
        PackageEntity updated = packageService.updatePackage(id, packages);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable("id") Long packageId){
        packageService.deletePackage(packageId);
        return "Đã xóa gói thành công";
    }
}
