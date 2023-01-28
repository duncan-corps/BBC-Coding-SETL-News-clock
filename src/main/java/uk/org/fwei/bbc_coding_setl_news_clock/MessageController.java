package uk.org.fwei.bbc_coding_setl_news_clock;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MessageController {

	private final AtomicReference<String> leftTabState;

	public MessageController(final AtomicReference<String> leftTabState) {
		this.leftTabState = leftTabState;
	}

	private final ModelAndView modelAndView(final String viewName) {
		final ModelAndView modelAndView = new ModelAndView(viewName, "model", new Model(leftTabState.get()));

		return modelAndView;
	}

	@RequestMapping
	public ModelAndView view() {
		return modelAndView("view");
	}

	// Should be Put, really
	@GetMapping("leftTab/{state}")
	public ModelAndView putLeftTabState(@PathVariable("state") final String newLeftTabState) {
		if ("on".equals(newLeftTabState) || "off".equals(newLeftTabState)) {
			final String oldLeftTabState = leftTabState.getAndSet(newLeftTabState);

			if (!Objects.equals(oldLeftTabState, newLeftTabState)) {
				System.out.println("Invoke leftTab('%s'[, 'BBC News HH:MM'])".formatted(newLeftTabState));
			}
		}

		return modelAndView("view");
	}
}
