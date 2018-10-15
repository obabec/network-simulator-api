package com.redhat.patriot.network_controll.model.routes;

/**
 * Calculated route. Including all necessary info describe routes.
 */
public class CalcRoute {
    private NextHop nextHop;
    private Integer cost;

    /**
     * Instantiates a new Calc route.
     *
     * @param nextHop the next hop
     * @param cost    the cost
     */
    public CalcRoute(NextHop nextHop, Integer cost) {
        this.nextHop = nextHop;
        this.cost = cost;
    }

    /**
     * Gets next hop.
     *
     * @return the next hop
     */
    public NextHop getNextHop() {
        return nextHop;
    }

    /**
     * Sets next hop.
     *
     * @param nextHop the next hop
     */
    public void setNextHop(NextHop nextHop) {
        this.nextHop = nextHop;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
