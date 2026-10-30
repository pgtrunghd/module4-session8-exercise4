package com.example.doctorservice.service;

import com.example.doctorservice.dto.DoctorResponse;
import com.example.doctorservice.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Optional<DoctorResponse> findById(Long id) {
        return doctorRepository.findById(id)
                .map(d -> new DoctorResponse(d.getId(), d.getName(), d.getSpecialization()));
    }

    public List<DoctorResponse> findAll() {
        return doctorRepository.findAll().stream()
                .map(d -> new DoctorResponse(d.getId(), d.getName(), d.getSpecialization()))
                .toList();
    }

    public List<DoctorResponse> searchByName(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name).stream()
                .map(d -> new DoctorResponse(d.getId(), d.getName(), d.getSpecialization()))
                .toList();
    }
}
