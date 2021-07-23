package br.com.caelum.carangobom.mocks;

import br.com.caelum.carangobom.domain.Dashboard;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DashboardMocks {

    public static Dashboard getChevroletDashboard() {
        Dashboard chevrolet = new Dashboard();
        chevrolet.setBrandName("Chevrolet");
        chevrolet.setTotalPrice(120000);
        chevrolet.setModelsAvailable(5);

        return chevrolet;
    }

    public static Dashboard getFordDashboard() {
        Dashboard ford = new Dashboard();
        ford.setBrandName("Ford");
        ford.setTotalPrice(90000);
        ford.setModelsAvailable(2);

        return ford;
    }

    public static List<Dashboard> getDashboardInformation() {
        return Arrays.asList(getChevroletDashboard(), getFordDashboard());
    }

}
