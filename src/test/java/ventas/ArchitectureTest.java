package ventas;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "ventas", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule el_dominio_no_debe_depender_de_adaptadores =
        noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAPackage("..adapters..")
            .allowEmptyShould(true);

    @ArchTest
    public static final ArchRule la_aplicacion_no_debe_depender_de_adaptadores =
        noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat()
            .resideInAPackage("..adapters..")
            .allowEmptyShould(true);
            
    @ArchTest
    public static final ArchRule el_dominio_no_debe_usar_frameworks =
        noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("java.sql..", "javax.swing..", "org.springframework..")
            .allowEmptyShould(true);
}
