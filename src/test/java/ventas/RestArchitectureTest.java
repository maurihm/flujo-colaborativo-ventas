package ventas;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "ventas", importOptions = {ImportOption.DoNotIncludeTests.class})
public class RestArchitectureTest {

    @ArchTest
    public static final ArchRule adaptadores_rest_no_deben_ser_accedidos_por_dominio =
            noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAPackage("..adapters.in.rest..")
            .allowEmptyShould(true);

    @ArchTest
    public static final ArchRule adaptadores_rest_no_deben_ser_accedidos_por_aplicacion =
            noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat()
            .resideInAPackage("..adapters.in.rest..")
            .allowEmptyShould(true);
}
