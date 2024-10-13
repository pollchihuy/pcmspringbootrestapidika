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

    /** SAAT MIGRASI FUNCTION UNTUK CAST DIUBAH , DISESUAIKAN DENGAN DATABASE MIGRASINYA NANTI */
    @Query("SELECT u FROM User u WHERE CAST(u.tanggalLahir AS string) LIKE ?1 order by u.id ")
    Page<User> findByTanggalLahirContainingIgnoreCase(String tanggalLahir,Pageable pageable);

    /** SAAT MIGRASI FUNCTION UNTUK SELISIH WAKTU DIUBAH , DISESUAIKAN DENGAN DATABASE MIGRASINYA NANTI */
    @Query(value = "SELECT u FROM User u WHERE CAST(TIMESTAMPDIFF(YEAR, u.tanggalLahir, CURDATE()) AS string ) LIKE ?1 order by u.id ")
    Page<User> cariUmur(String tanggalLahir, Pageable pageable);

    List<User> findByEmailContainingIgnoreCase(String email);
    List<User> findByNamaLengkapContainingIgnoreCase(String namaLengkap);
    List<User> findByAlamatContainingIgnoreCase(String alamat);
    List<User> findByNoHpContainingIgnoreCase(String noHp);

    /** SAAT MIGRASI FUNCTION UNTUK CAST DIUBAH , DISESUAIKAN DENGAN DATABASE MIGRASINYA NANTI */
    @Query("SELECT u FROM User u WHERE CAST(u.tanggalLahir AS string) LIKE ?1")
    List<User> findByTanggalLahirContainingIgnoreCase(String tglLahir);

    /** SAAT MIGRASI FUNCTION UNTUK SELISIH WAKTU DIUBAH , DISESUAIKAN DENGAN DATABASE MIGRASINYA NANTI */
//    @Query(value = "SELECT u.* FROM mst_user u WHERE CAST(TIMESTAMPDIFF(YEAR, u.tanggal_lahir, CURDATE()) AS string) LIKE ?1 ",nativeQuery = true)
    @Query(value = "SELECT u FROM User u WHERE CAST(TIMESTAMPDIFF(YEAR, u.tanggalLahir, CURDATE()) AS STRING ) LIKE ?1 ")
    List<User> cariUmur(String tanggalLahir);

    /** UNTUK REGISTRASI */
    Optional<User> findByEmail(String value);
    Optional<User> findByEmailAndIsRegistered(String value, Boolean isRegistered);

}