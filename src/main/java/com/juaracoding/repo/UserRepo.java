package com.juaracoding.repo;

import com.juaracoding.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    List<User> findTop1ByUsernameOrNoHpOrEmail(String usr, String noHp, String mail);

    /** UNTUK LOGIN PASTI HANYA MENGEMBALIKAN 1, AKAN TETAPI KITA TIDAK TAU SI USER INPUT NOHP,EMAIL ATAUPUN USERNAME */
    Optional<User> findTop1ByUsernameOrNoHpOrEmailAndIsRegistered(String usr, String noHp, String mail, Boolean isRegis);

    Page<User> findByEmailContainingIgnoreCase(Pageable pageable,String email);
    Page<User> findByNamaLengkapContainingIgnoreCase(Pageable pageable,String namaLengkap);
    Page<User> findByAlamatContainingIgnoreCase(Pageable pageable,String alamat);
    Page<User> findByNoHpContainingIgnoreCase(Pageable pageable,String noHP);
    @Query("SELECT u FROM User u WHERE CAST(u.tanggalLahir AS string) = ?1 order by u.id ")
    Page<User> findByTanggalLahirContainingIgnoreCase(String tanggalLahir,Pageable pageable);

    List<User> findByEmailContainingIgnoreCase(String email);
    List<User> findByNamaLengkapContainingIgnoreCase(String namaLengkap);
    List<User> findByAlamatContainingIgnoreCase(String alamat);
    List<User> findByNoHpContainingIgnoreCase(String noHp);
    List<User> findByUsernameContainingIgnoreCase(String username);
    @Query("SELECT u FROM User u WHERE CAST(u.tanggalLahir AS string) = ?1")
    List<User> findByTanggalLahirContainingIgnoreCase(String tglLahir);

    /** UNTUK REGISTRASI */
    Optional<User> findByEmail(String value);
    Optional<User> findByEmailAndIsRegistered(String value, Boolean isRegistered);

}