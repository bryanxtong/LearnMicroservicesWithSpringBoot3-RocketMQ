package microservices.book.gateway.sentinel;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

@Configuration
public class SentinelConfig {

    @Bean
    public List<DegradeRule> loadDegradeRules() {
        try (InputStream inputStream = new ClassPathResource("degrade-rules.json").getInputStream()) {
            String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            List<DegradeRule> rules = JSON.parseObject(json, new TypeReference<>() {
            });
            DegradeRuleManager.loadRules(rules);

            return rules;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load degrade rules", e);
        }
    }

    @Bean
    public List<GatewayFlowRule> loadFlowRules() {
        try (InputStream inputStream = new ClassPathResource("gateway.json").getInputStream()) {
            String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            List<GatewayFlowRule> rules = JSON.parseObject(json, new TypeReference<>() {
            });
            GatewayRuleManager.loadRules(new HashSet<>(rules));
            return rules;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load gateway flow rules", e);
        }
    }
}
