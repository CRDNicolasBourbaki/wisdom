package org.wisdom.api.interception;


import org.wisdom.api.http.Result;
import org.wisdom.api.router.Route;

import java.util.regex.Pattern;

/**
 * Service offered by filters. Filters are services called by the router to intercept the request.
 *
 * A filter <em>intercepts</em> request and can customize the input and output of the request. Unlike {@link
 * Interceptor}, filters are not attached to action method but on URIs. Actually, interceptors are specialized filters.
 *
 * Filters are invoked before interceptors. When several filters are used on a single request,
 * a filter chain is built. the order of the chain depends on the integer returned by the
 * {@link org.wisdom.api.interception.Filter#priority()} method. Filter with a high priority are before filters with
 * lower priority.
 *
 * Filter must call the {@link org.wisdom.api.interception.RequestContext#proceed()} method to call the next
 * filter. At the end of the filter chain, the interceptor chain is invoked.
 *
 * The route handled by the filter are selected by the {@link org.wisdom.api.interception.Filter#uri()} method.
 */
public interface Filter {

    /**
     * The interception method. The method should call {@link RequestContext#proceed()}
     * to call the next interceptor. Without this call it cuts the chain.
     * @param context the filter context
     * @return the result
     * @throws Throwable if anything bad happen
     */
    public abstract Result call(Route route, RequestContext context) throws Throwable;

    /**
     * Gets the Regex Pattern used to determine whether the route is handled by the filter or not.
     * Notice that the router are caching these patterns and so cannot changed.
     */
    public Pattern uri();

    /**
     * Gets the filter priority, determining the position of the filter in the filter chain. Filter with a high
     * priority are called first. Notice that the router are caching these priorities and so cannot changed.
     *
     * It is heavily recommended to allow configuring the priority from the Application Configuration.
     * @return the priority
     */
    public int priority();


}