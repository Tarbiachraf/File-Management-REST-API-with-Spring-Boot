package com.example.apiFileManagementSystem.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {

    private String firstName;

    private String lastName;

    private String email;
  /*  logging.level.org.hibernate.SQL=DEBUG
    logging.level.org.hibernate.type=TRACE
    spring.jpa.properties.hibernate.format_sql=true

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository; // Assurez-vous d'importer la classe UserRepository ou le service approprié

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;
    @Override
    public void run(String... args) throws Exception {
        // Vous pouvez ajouter des utilisateurs ici en utilisant le userRepository


        AppUser user2 = new AppUser();
        user2.setFirstName("achraf");
        user2.setLastName("tarbi");
        user2.setPassword(passwordEncoder.encode("123456789"));
        user2.setEmail("achraf@example.com");
        user2.setRoleEnum(RoleEnum.ADMIN);

        AppFile file1 = new AppFile();
        file1.setName("jee");

        Folder folder = new Folder();
        folder.setNom("ficher10");
        //folder.setChildrenFiles(Arrays.asList(file1));
        folder.setUser(user2);
        file1.setDossier(folder);
        //user2.setFolders(Arrays.asList(folder));



        // Enregistrez les utilisateurs dans la base de données
       // userRepository.save(user2);
        //userRepository.save(user2);
       //folderRepository.save(folder);
       //fileRepository.save(file1);






        // Vous pouvez ajouter d'autres utilisateurs selon vos besoins
    }

    */

}
