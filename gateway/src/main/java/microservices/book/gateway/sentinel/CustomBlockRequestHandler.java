package microservices.book.gateway.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomBlockRequestHandler implements BlockRequestHandler {
    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
        if (t instanceof FlowException || t instanceof ParamFlowException) {
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.TEXT_PLAIN).bodyValue("Blocked by Sentinel: " + t.getClass().getSimpleName());
        } else if (t instanceof DegradeException) {
            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.TEXT_PLAIN).bodyValue("Blocked by Sentinel: " + t.getClass().getSimpleName());
        } else {
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).bodyValue("Internal Server Error: " + t.getClass().getSimpleName());
        }
    }
}