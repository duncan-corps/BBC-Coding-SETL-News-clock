package uk.org.fwei.bbc_coding_setl_news_clock;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ViewController {

	private final LeftTabService leftTabService;

	public ViewController(final LeftTabService leftTabService) {
		this.leftTabService = leftTabService;
	}

	private final ModelAndView modelAndView(final String viewName) {
		final ModelAndView modelAndView = new ModelAndView(viewName, "leftTab", leftTabService.model());

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
			final String oldLeftTabState = leftTabService.getAndSetState(newLeftTabState);

			if (!Objects.equals(oldLeftTabState, newLeftTabState)) {
				leftTabService.invoke();
			}
		}

		return modelAndView("view");
	}
}
